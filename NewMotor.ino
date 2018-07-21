
#define motor1 10
#define ctrlpin1 =2
#define ctrlpin2=3
#define enable = 9;
int valx=0;
int valy=0;
const int maxspeed=200;
void setup() {                
  Serial.begin(9600);
  pinMode(ctrlpin1,OUTPUT);
  pinMode(ctrlpin2,OUTPUT);
  pinMode(enable,OUTPUT);
  digitalWrite(enable,HIGH);
  digitalWrite(ctrlpin1,HIGH);
  digitalWrite(ctrlpin2,LOW);
}
void loop() {
  valx=analogRead(A0);
  valy=analogRead(A1);
  
  valx=map(valx,0,1023,-255,255);
  valy=map(valy,0,1023,-255,255);
  Serial.print(valx);
  Serial.print("  ");
  Serial.println(valy);
  
  if(valy>125){
     turn_left();
  }
  else if(valy<-125){
    turn_right();
  }
  else{
    speed_up(valx);
  }
    
}
void turn_right(){
   analogWrite(enable,50);
   //analogWrite(motor2,0);
}
void turn_left(){
  //analogWrite(motor2,50);
 // analogWrite(motor1,0);
	analogWrite(enable,0);
}
void speed_up(int v){
  if(v<=maxspeed){
	  analogWrite(enable,v);
    //analogWrite(motor1,v);
    //analogWrite(motor2,valx);
  }
  else{
	  analogWrite(enable,maxspeed);
    //analogWrite(motor1,maxspeed);
    //analogWrite(motor2,maxspeed);
  } 
}