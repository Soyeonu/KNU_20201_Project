#include <SoftwareSerial.h>
#include "DHT.h"


#define rx 3
#define tx 2
#define DB true
#define Buzzer 7
#define Seat 12
#define Temperature 8
#define Reed 10

String income_wifi = "";
int flag = 0;
int SeatOrNot = 0;

SoftwareSerial esp(tx, rx);
DHT dht(Temperature, 11);


void setup()
{
  Serial.begin(115200);
  esp.begin(115200);
  sendData("AT+CIPMUX=1\r\n", 1000, DB, ""); // configure for multiple connections
  sendData("AT+CIPSERVER=1,80\r\n", 1000, DB, ""); // turn on server on port 80
}

void loop()
{
  if (esp.available())
  {
    if (flag == 0) //앱 실행과 동시에 좌석 및 안전벨트 착용 여부 확인
    {
      String str = "";
      delay(5000);
      pinMode(Seat, INPUT_PULLUP);
      if (digitalRead(12) == LOW)
      {
        str = "Sitting"; //앉아있는 경우
        SeatOrNot = 1;
      }
      else str = "No Sitting"; //앉아있지 않는 경우
      str += "\r\n";
      String cmd = "AT+CIPSEND=0,";
      cmd += String(str.length()) + "\r\n";
      sendData(cmd, 1000, DB, str);

      if (SeatOrNot == 1)
      {
        pinMode(Reed, INPUT);
        pinMode(Buzzer, OUTPUT);
        int val = digitalRead(10);

        delay(5000);

        if (val == LOW)
        {
          String str = "Not Reed\r\n";
          String cmd = "AT+CIPSEND=0,";
          cmd += String(str.length()) + "\r\n";
          sendData(cmd, 1000, DB, str);
          tone(Buzzer, 500, 1000); 
          delay(1000);
        }
        else
        {
          String str = "Reed\r\n";
          String cmd = "AT+CIPSEND=0,";
          cmd += String(str.length()) + "\r\n";
          sendData(cmd, 1000, DB, str);
        }
      }

      flag = 1;
    }


    if (flag == 1)
    {
      income_wifi = esp.readString();
      int first = income_wifi.indexOf(":");
      String input = income_wifi.substring(first + 1);
      Serial.println("From Master:" + income_wifi); //아두이노에 input이 있을 때 명령어 출력
      Serial.println("Permission:" + input); //명령어만 골라서 출력


      if (input.equals("TEMP_CHK\n")) //온습도 센서에서 온도값을 넘김
      {
        int t = dht.readTemperature();
        String str = String(t);
        str += "deg\r\n";
        String cmd = "AT+CIPSEND=0,";
        cmd += String(str.length()) + "\r\n";
        sendData(cmd, 1000, DB, str);
      }

      else if (input.equals("TEMP_ON\n")) //에어컨 = LED 전구
      {
        Serial.println("에어컨을 켜주세요!");
      }

      else if (input.equals("TEMP_OFF\n"))
      {
        Serial.println("에어컨을 꺼주세요!");
      }


      else if (input.equals("TEMP_TUP\n"))
      {
        //value값까지 온도 올리기
      }
      else if (input.equals("TEMP_TDN\n"))
      {
        //value값까지 온도 낮추기
      }
      else if (input.equals("TEMP_DST\n"))
      {
        //온습도 센서값이 value가 되면 LED 끄기
      }

      
    }

    
  }
}


String sendData(String command, const int timeout, boolean debug, String s)
{
  int exit_flag = 0;
  String response = "";
  esp.println(command); //와이파이 모듈에게 AT cmd 전달
  long int time = millis();

  while (exit_flag == 0 && (time + timeout) > millis()) {
    while (esp.available()) {
      char c = esp.read(); //와이파이 모듈에서 값 읽어오기
      if (c == '>')
      {
        exit_flag = 1;
        esp.print(s);
        break;
      }
      response += c;
    }
  }
  if (debug) Serial.println(response);
  exit_flag = 0;
  return response;
}
