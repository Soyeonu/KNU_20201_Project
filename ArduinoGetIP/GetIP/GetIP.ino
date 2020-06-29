#include <SoftwareSerial.h>

#define rxPin 5

#define txPin 4

#define DB true
SoftwareSerial esp01(txPin, rxPin); // SoftwareSerial NAME(TX, RX);


//입력 명령어 순서
//AT+CIPMUX=1;
//AT+CWJAP="SSID","PWD"
//AT+CIPSERVER=1,333
//AT+CIFSR  -> Master에 Thread값에 입력해야하는 IP주소
void setup() {
  Serial.begin(115200);   //시리얼모니터
  esp01.begin(115200);  //와이파이 시리얼

}



void loop() {

  if (esp01.available()) {       

    Serial.write(esp01.read());  //블루투스측 내용을 시리얼모니터에 출력

  }  

  if (Serial.available()) {         

    esp01.write(Serial.read());  //시리얼 모니터 내용을 블루추스 측에 쓰기

  }

}
