// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;

public class RobotContainer {

	private CommandPS5Controller controller = new CommandPS5Controller(0);
	private SparkMax flywheelMotor = new SparkMax(1, MotorType.kBrushless);
	private SparkMax transportMotor = new SparkMax(2, MotorType.kBrushless);
	Subsystem subsystem = new Subsystem();

	public RobotContainer() {
		configureBindings();
	}

	public void joystickShootingSeparate() {
		double flywheelMotorInput = Math.pow(controller.getRightY(), 3);
		double transportMotorInput = Math.pow(controller.getLeftY(), 3);
		flywheelMotor.set(flywheelMotorInput);
		transportMotor.set(transportMotorInput);
	}

	public void joystickShootingTogether() {
		double input = Math.pow(controller.getRightY(), 3);
		flywheelMotor.set(input);
		transportMotor.set(-input);
	}

	public Command defaultJoystickShootingCommand() {
		return Commands.run(() -> joystickShootingTogether(), subsystem);
	}

	public Command separateJoystickShootingCommand() {
		return Commands.run(() -> joystickShootingSeparate(), subsystem);
	}

	private void configureBindings() {
		subsystem.setDefaultCommand(defaultJoystickShootingCommand());
		controller.L2().whileTrue(separateJoystickShootingCommand());
	}

	public Command getAutonomousCommand() {
		return Commands.print("No autonomous command configured");
	}
}
