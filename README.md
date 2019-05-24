# 2019_Krokomierz


# Overview

Device built using the STM32 board, which counts user’s steps and shows the amount 
using the Android app.


# Description

Ca(t/r)s app shows steps counted by the device. There are two game modes: Cats and Cars.

In the Cats mode you have to feed your cat by doing 10000 steps during the day. If the cat hasn’t been fed for the 3 days in a row, he’s starting to die of hunger. To save him, you have to do additional 3000 steps. If during last 3 days the cat hasn’t been fed, and in one of these days you wouldn’t have done any step (saved in the app), the cat would eventually die – you’ll have to do 15000 steps to the pet shop, and after that make another 10000 to feed your new cat.

Cars mode is more arcade, there are  no bigger consequences for failing the aim. Each day the app draws different car (one of the 2019 FIA Formula 1 field), each one having different damage possibility and different amount of steps needed to make it refueled. After the randomly selected step the car may have an issue – you have to do additional 2000 steps to repair it.

You can save the progress anytime – saving data is the key for the proper work of an app. Database, which holds the whole data can be shown by clicking the ‘database’ button on the main screen of the app.


# Tools

### Physical compontents:
- STM32407FG Discovery
- LIS3DSH accelerometer
- HC-05 Bluetooth Module
- Powerbank

### Software:
- System WorkBench for STM32
- STM32CubeMX
- Android Studio

### Languages/Libraries:
- C language
- DSP library
- Kiss_fft library
- Lis3dsh library
- Java language


# How to run

You have to install Ca(t/r)s app on the device with the Android system and plug in the board with linked HC-05 bluetooth module into the powerbank. Then, you have to pair the module with the device by using the ‘1234’ code, and then connect it by the ‘connect’ button in the main screen of the app.


# How to compile

You have to run the .cproject file, build the project (ctrl + B), and then run the project (Run -> Run As -> Ac6 STM32 C/C++ Application).


# License

MIT


# Credits

### Creators:
- Paulina Kukuła
- Jakub Małecki

The project was conducted during the Microprocessor Lab course held by the Institute of Control and Information Engineering, Poznan University of Technology. Supervisor: Tomasz Mańkowski

