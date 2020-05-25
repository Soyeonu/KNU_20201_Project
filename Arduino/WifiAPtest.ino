#include <SoftwareSerial.h>
#include "DHT.h"


#define rx 3
#define tx 2
#define DB true
#define ledPin 8
#define Buzzer 7
#define Switch 12


String income_wifi="";

SoftwareSerial esp(tx, rx);
DHT dht(8,11);


void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  esp.begin(115200);
  sendData("AT+CIPMUX=1\r\n",1000,DB,""); // configure for multiple connections
  sendData("AT+CIPSERVER=1,80\r\n",1000,DB,""); // turn on server on port 80
}

void loop() {
  // put your main code here, to run repeatedly:
  if(esp.available())
  {
      income_wifi = esp.readString();
      char temp = income_wifi.charAt(11);
      Serial.write(temp);

      switch(temp)
      {
        case '1': //Master에서 1을 보내면 온습도 센서에서 온도값을 넘김
              {
              int t = dht.readTemperature();
              String str = String(t);
              str+="deg\r\n";
              String cmd = "AT+CIPSEND=0,";
              cmd += String(str.length()) + "\r\n";
              sendData(cmd, 1000, DB, str);
              break;
              }
        case '2': //Master에서 2를 보내면 좌석에 앉아있는지 유무를 알려줌
              {
               String str="";
               pinMode(Switch, INPUT_PULLUP);
               pinMode(Buzzer, OUTPUT);
               delay(1000);
               if(digitalRead(12) == LOW){ //앉아있는 경우
                str = "Sitting";
               }
               else 
               {
                str = "No Sitting"; //앉아있지 않는 경우
                tone(Buzzer,500,1000); //부저비트 소리 남 -> 이 부분을 안전벨트 착용으로 옮길 예정
               }
               str += "\r\n";
               String cmd = "AT+CIPSEND=0,";
               cmd += String(str.length()) + "\r\n";
               sendData(cmd, 1000, DB, str);
               break;
              }
        case '3':
              {
                break;
              }
      }
  }
}

String sendData(String command, const int timeout, boolean debug, String s)
{
  int exit_flag = 0;
  String response="";
  esp.print(command); //와이파이 모듈에게 AT cmd 전달
  long int time = millis();
  
  while(exit_flag == 0 && (time+timeout)>millis()){
    while(esp.available()){
      char c = esp.read(); //와이파이 모듈에서 값 읽어오기
      if(c == '>')
      {
        exit_flag = 1;
        esp.print(s);
        break;
      }
      response+=c;
    }
  }
  if(debug) Serial.print(response);
  exit_flag = 0;
  return response;
  }
