#include <VirtualWire.h>
//char *controller;

int x = 0;
int y = 0;
int x_d = 1;
int y_d = 1;


void setup() {
  vw_set_ptt_inverted(true); //
  vw_set_tx_pin(12);
  vw_setup(2000);// speed of data transfer Kbps
  Serial.begin(9600);
}

void loop(){
  x = analogRead(A0);
  y = analogRead(A1);
  x = map(x,0,1023,-255,255);
  y = map(y,0,1023,-255,255);
  /*Serial.println(x);
  Serial.println(y_d);*/
  if (y>=150){
    y_d=1;
    x_d=1;
    y=1;
    x=2;
    }
  else if(y<=-150){
    y_d=1;
    x_d=1;
    y=2;
    x=1;
    }
  else if (x>=0) {
    x_d = 1; 
    y_d=1;
    if(x>=150){//novo
      x=1;
      y=1;
    }
    else{
      x=2;
      y=2;
    }  
  }
  else {
    x_d = 2;
    y_d=2;
    if(x<=-150){
      x=1;
      y=1;
    }
    else{
      x=2;
      y=2;
    }  
  }
  char msg_x=(char)x;
  char msg_y=(char)y;
  char dir_x=(char)x_d;
  char dir_y=(char)y_d;
  char controller[] = {msg_x, dir_x, msg_y, dir_y};
  vw_send((uint8_t *)controller, strlen(controller));
  vw_wait_tx(); // Wait until the whole message is gone
  delay(50);

