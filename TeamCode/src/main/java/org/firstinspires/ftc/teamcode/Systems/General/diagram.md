// C O N T R O L   H U B

M0: left_front_drive   

M1: left_back_drive   (Left Encoder Here)

M2: right_back_drive   (Back Encoder Here)

M3: right_front_drive   
    S0: liftingServo S1:LEDs   S2:  S3:  S4:


// E X P A N S I O N   H U B

M0: liftOutboard 

M1:

M2: liftYaw

M3: intake (Right encoder here)
    S0:trapdoorServo   S1:boxServo   S2:droneServo S3:   S4: 
DIGITAL     0/1: boxTouch






Trapdoor thingy
//            if(wasAPressed > 0){
//                if((getRuntime() - wasAPressed) > 0.5){
//                    wasAPressed = 0;
//                }
//                else{
//                    return;
//                }
//            }
//            wasAPressed = getRuntime();
