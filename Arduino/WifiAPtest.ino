#include <SoftwareSerial.h>
#include "DHT.h"


#define rx 5
#define tx 4
#define DB true
#define Buzzer 6
#define Seat 10
#define Temperature 8
#define Reed 3


String income_wifi = "";
int flag = 0;
int SeatOrNot = 0;
int SeatBelt = 0;
int cnt = 0;

SoftwareSerial esp(tx, rx);
DHT dht(Temperature, 11);


void setup()
{
  Serial.begin(115200);   //시리얼모니터
  esp.begin(115200);  //와이파이 시리얼
  sendData("AT+CIPMUX=1\r\n", 1000, DB, ""); 

  //현재 접속한 공유기 정보를 입력하셔야 합니다!
  //SSID = 행갱갱
  //PWD = 01030361070
  String Connect = "AT+CWJAP=""\"행갱갱\""",""\"01030361070\"";
  Connect = Connect+"\r\n";
  sendData(Connect, 5000, DB, "");

  //port 번호는 333으로 고정
  String Port = "AT+CIPSERVER=1,333\r\n";
  sendData(Port, 1000, DB, "");
}

void loop()
{
  if (esp.available())
  {
    if (flag == 0) //앱 실행과 동시에 좌석 및 안전벨트 착용 여부 확인
    {
      delay(3000);
      pinMode(Seat, INPUT);
      if (digitalRead(Seat) == LOW)
        SeatOrNot = 1;

      Serial.println("Seat:" + String(SeatOrNot));

      if (SeatOrNot == 1)
      {
        pinMode(Reed, INPUT);
        pinMode(Buzzer, OUTPUT);
        int val = digitalRead(Reed);

        delay(5000);

        if (val == LOW) //나중에 LOW로 바꿔야함
        {
          SeatBelt = 0;
          tone(Buzzer, 500, 1000);
          delay(1000);
        }
        else
        {
          SeatBelt = 1;
        }
        flag = 1;
      }
    }

    if (flag == 1)
    {
      income_wifi = esp.readString();
      int first = income_wifi.indexOf(":");
      int second = income_wifi.indexOf("/");
      int third = income_wifi.indexOf("\n");
      String input = income_wifi.substring(first + 1, second);
      String value = income_wifi.substring(second + 1,third);
      Serial.println(income_wifi); //input 명령어 출력


      if (input.equals("TEMP_CHK")) //온습도 센서에서 온도값을 넘김
      {
        int t = dht.readTemperature();
        String str = String(t)+"\r\n";
        String cmd = "AT+CIPSEND=0,";
        cmd += String(str.length()) + "\r\n";
        sendData(cmd, 1000, DB, str);
      }

      else if (input.equals("TEMP_ON")) //에어컨 = LED 전구
      {
        Serial.print("에어컨을 켜주세요\r\n");
        String str = "ON!";
        String cmd = "AT+CIPSEND=0,";
        cmd += String(str.length()) + "\r\n";
        sendData(cmd, 1000, DB, str);

      }
      else if (input.equals("TEMP_OFF"))
      {
        Serial.print("에어컨을 꺼주세요\n");
        String str = "OFF!";
        String cmd = "AT+CIPSEND=0,";
        cmd += String(str.length()) + "\r\n";
        sendData(cmd, 1000, DB, str);
      }

      else if (input.equals("TEMP_DST"))
      {
        Serial.println("현재 지정한 온도는 " + value + " 입니다.");
      }
    }
  }
}


String sendData(String command, const int timeout, boolean debug, String s)
{
  int exit_flag = 0;
  String response = "";
  esp.print(command); //와이파이 모듈에게 AT cmd 전달
  long int time = millis();

  while (exit_flag == 0 && (time + timeout) > millis()) {
    while (esp.available()) {
      char c = esp.read(); //와이파이 모듈에서 값 읽어오기
      if (c == '>')
      {
        exit_flag = 1;
        Serial.print(s);
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
