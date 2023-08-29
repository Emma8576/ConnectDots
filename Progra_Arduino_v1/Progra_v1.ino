#define led 8
#define boton 7
int estado;

void setup(){
  pinMode(led,OUTPUT);
  pinMode(boton,INPUT);
}

void loop(){
  estado = digitalRead(boton);
  if (estado==HIGH){
    digitalWrite(led,HIGH);
  }
  else{digitalWrite(led,LOW);
  }
}