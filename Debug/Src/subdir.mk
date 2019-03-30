################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../Src/lis3dsh.c \
../Src/main.c \
../Src/stm32_tm1637.c \
../Src/stm32f4xx_hal_msp.c \
../Src/stm32f4xx_it.c \
../Src/system_stm32f4xx.c \
../Src/tm_stm32f4_lis302dl_lis3dsh.c 

OBJS += \
./Src/lis3dsh.o \
./Src/main.o \
./Src/stm32_tm1637.o \
./Src/stm32f4xx_hal_msp.o \
./Src/stm32f4xx_it.o \
./Src/system_stm32f4xx.o \
./Src/tm_stm32f4_lis302dl_lis3dsh.o 

C_DEPS += \
./Src/lis3dsh.d \
./Src/main.d \
./Src/stm32_tm1637.d \
./Src/stm32f4xx_hal_msp.d \
./Src/stm32f4xx_it.d \
./Src/system_stm32f4xx.d \
./Src/tm_stm32f4_lis302dl_lis3dsh.d 


# Each subdirectory must supply rules for building sources it contributes
Src/%.o: ../Src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: MCU GCC Compiler'
	@echo $(PWD)
	arm-none-eabi-gcc -mcpu=cortex-m4 -mthumb -mfloat-abi=hard -mfpu=fpv4-sp-d16 '-D__weak=__attribute__((weak))' '-D__packed=__attribute__((__packed__))' -DUSE_HAL_DRIVER -DSTM32F407xx -I"D:/Politechnika/PTM_projekt/2019_Krokomierz/Inc" -I"D:/Politechnika/PTM_projekt/2019_Krokomierz/Drivers/STM32F4xx_HAL_Driver/Inc" -I"D:/Politechnika/PTM_projekt/2019_Krokomierz/Drivers/STM32F4xx_HAL_Driver/Inc/Legacy" -I"D:/Politechnika/PTM_projekt/2019_Krokomierz/Drivers/CMSIS/Device/ST/STM32F4xx/Include" -I"D:/Politechnika/PTM_projekt/2019_Krokomierz/Drivers/CMSIS/Include"  -Og -g3 -Wall -fmessage-length=0 -ffunction-sections -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


