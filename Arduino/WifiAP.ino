#include <SoftwareSerial.h>
#include "DHT.h"
#define rx 3
#define tx 2
#define DEBUG true
#define ledPin 8

String income_wifi="";

SoftwareSerial esp(tx, rx);
DHT dht(8,11);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  esp.begin(115200);
  sendData("AT+CIPMUX=1\r\n",1000,DEBUG,""); // configure for multiple connections
  sendData("AT+CIPSERVER=1,80\r\n",1000,DEBUG,""); // turn on server on port 80
}

void loop() {
  // put your main code here, to run repeatedly:
  if(esp.available())
  {
      income_wifi = esp.readString();
      char temp = income_wifi.charAt(11);
      Serial.write(temp);

    //LED 연결하여 테스트한 부분
    //temp=1, 2
    if(temp == '1')
    {
      digitalWrite(ledPin,HIGH);
      delay(1000);
    }
    else if(temp == '2')
    {
      digitalWrite(ledPin,LOW);
      delay(1000);
    }

    //temp=3이 입력되면
    //DHT11센서에서 온도정보를 가져와서 보냄
    else if(temp == '3')
    {
      int t = dht.readTemperature();
      String str = String(t);
      str+="\r\n";
      String cmd = "AT+CIPSEND=0,";
      cmd += String(str.length()) + "\r\n";
      sendData(cmd, 1000, DEBUG, str);
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
