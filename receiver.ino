
#include <VirtualWire.h>
const int ctrlpin1 =2;
const int ctrlpin2 =3;
const int ctrlpin3 =4;
const int ctrlpin4 =6;
const int enable1 = 11;
const int enable2 = 5;
const int table[] = {1,2};
int valx = 0;
int valy = 0;
int vx;
int dirx;
int vy;
int diry;
const int maxspeed = 200;

void set_speed(int x,int y, int dx, int dy){
	//int xis=x;//nao deixar chegar ao nível 255
        //int yps=y;
	if (dx==1){
		digitalWrite(ctrlpin1,HIGH);
		digitalWrite(ctrlpin2,LOW);
	}
	else{
		digitalWrite(ctrlpin1,LOW);
		digitalWrite(ctrlpin2,HIGH);
	}
	if(dy==1){
		digitalWrite(ctrlpin3,HIGH);
		digitalWrite(ctrlpin4,LOW);
	}
	else {
		digitalWrite(ctrlpin3,LOW);
		digitalWrite(ctrlpin4,HIGH);
	}
        analogWrite(enable1,abs(x));
	analogWrite(enable2,abs(y));
}
void setup() {
  
  //------------------------------------------------------------
  // Receptor
  vw_set_ptt_inverted(true); // Required for DR3100
  vw_set_rx_pin(12);
  vw_setup(2000);  // Bits per sec
  vw_rx_start();       // Start the receiver PLL running
  Serial.begin(9600);
  
  //------------------------------------------------------------3
  //Motorer
  pinMode(ctrlpin1,OUTPUT);
  pinMode(ctrlpin2,OUTPUT);
  pinMode(ctrlpin3,OUTPUT);
  pinMode(ctrlpin4,OUTPUT);
  pinMode(enable1,OUTPUT);
  digitalWrite(enable1,HIGH);
  pinMode(enable2,OUTPUT);
  digitalWrite(enable2,HIGH);
  digitalWrite(ctrlpin1,HIGH);
  digitalWrite(ctrlpin2,LOW);
  digitalWrite(ctrlpin3,HIGH);
  digitalWrite(ctrlpin4,LOW);
  analogWrite(enable2,0);
  analogWrite(enable1,0);
  //-------------------------------------------------------------;
}
void loop() {
  
  //-------------------------------------------------------------
  // Receptor
  uint8_t buf[VW_MAX_MESSAGE_LEN];
  uint8_t buflen = VW_MAX_MESSAGE_LEN;
  bool msg = vw_wait_rx_max(4000);
  if(!msg){
    set_speed(0,0,1,1);
    Serial.println("comoff");
  }
  else{  
    //if (vw_get_message(buf, &buflen)) {// Non-blocking
      vw_get_message(buf,&buflen);
      vx = (int)buf[0];
      dirx = (int)buf[1];
      vy = (int)buf[2];
      diry = (int)buf[3];
      if((dirx!=1 && dirx!= 2) || (diry!=1 && diry!=2)){
      }
      else {     
        if(vy==1 && vx==2){
          vy=150;
          vx=0;
          dirx=1;
          diry=2;
          }
        else if (vy==2 && vx==1){
          vx=150;
          vy=0;
          dirx=1;
          diry=1;
        }
        else if (vy==1 && vx ==1){
          vx=maxspeed;
          vy=vx;
        }
        else if (vx==2 && vy==2) {
          vx=0;
          vy = 0;
        }
        else {
          vx=0;
          vy=vx;
        }
        set_speed(vx,vy,dirx,diry);
        Serial.print("x: ");
        Serial.println(vx);
        Serial.print("y :");
        Serial.println(vy);
        Serial.print("dx:");
        Serial.println(dirx);
        Serial.print("dy:");
        Serial.println(diry);
        }
    //}
    
  //-------------------------------------------------------------  

  }
}
