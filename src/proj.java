import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import devices.*;
import command.*;
import decorator.*;
import singleton.*;
import factory.*;
import observer.*;
import composite.*;
import mode.*;


public class proj extends Application {

    private DeviceManager deviceManager = DeviceManager.getInstance();
    private Invoker remote = new Invoker();
    private ConcreteSubject securitySystem = new ConcreteSubject();

    private ModeStrategy currentMode;
    private MotionSensorDecorator motionSensorLight;
    private DimmableLightDecorator dimmableLight;

    private Composite livingRoom;
    private Composite kitchen;
    private Composite bedroom1;
    private Composite bedroom2;
    private Composite bathroom;

    private TextArea activityLog = new TextArea();
    private Label logLabel;


    private Light light;
    private Thermostat thermostat;
    private DoorLock doorLock;

    private ComboBox<String> roomSelector = new ComboBox<>();
    private Composite currentRoom;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Smart Home Automation");

        // Inicjalizacja urządzeń
        light = (Light) DeviceFactory.createDevice("Light");
        thermostat = (Thermostat) DeviceFactory.createDevice("Thermostat");
        doorLock = (DoorLock) DeviceFactory.createDevice("DoorLock");

        motionSensorLight = new MotionSensorDecorator(light);
        dimmableLight = new DimmableLightDecorator(light);

        deviceManager.addDevice("Light", light);
        deviceManager.addDevice("Thermostat", thermostat);
        deviceManager.addDevice("DoorLock", doorLock);

        ConcreteObserver securityApp = new ConcreteObserver("Mobile Security App");
        securitySystem.registerObserver(securityApp);

        // Konfiguracja pokojów
        livingRoom = createRoom("Living Room");
        kitchen = createRoom("Kitchen");
        bedroom1 = createRoom("Bedroom1");
        bedroom2 = createRoom("Bedroom2");
        bathroom = createRoom("Bathroom");

        currentRoom = livingRoom; // Domyślny pokój

        VBox root = createGUI();
        Scene scene = new Scene(root, 1000, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Composite createRoom(String name) {
        Composite room = new Composite(name);
        room.add(new Leaf("Light", (Light) DeviceFactory.createDevice("Light")));
        room.add(new Leaf("Thermostat", (Thermostat) DeviceFactory.createDevice("Thermostat")));
        room.add(new Leaf("DoorLock", (DoorLock) DeviceFactory.createDevice("DoorLock")));
        return room;
    }

    private VBox createGUI() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label roomLabel = createStyledLabel("Select Room:", "#7e57c2");
        roomSelector.getItems().addAll("Living Room", "Kitchen", "Bedroom1", "Bedroom2", "Bathroom");
        roomSelector.setValue("Living Room");
        roomSelector.setOnAction(e -> switchRoom());
        HBox roomBox = new HBox(10, roomLabel, roomSelector);

        Label modeLabel = createStyledLabel("System Modes:", "#7e57c2");
        Button dayModeBtn = createStyledButton("Day Mode", "#ffeb3b", e -> activateMode(new DayMode()));
        Button nightModeBtn = createStyledButton("Night Mode", "#ffeb3b", e -> activateMode(new NightMode()));
        Button ecoModeBtn = createStyledButton("Eco Mode", "#ffeb3b", e -> activateMode(new EcoMode()));
        Button vacationModeBtn = createStyledButton("Vacation Mode", "#ffeb3b", e -> activateMode(new VacationMode()));
        Button customModeBtn = createStyledButton("Custom Mode", "#d1c4e9", e -> openCustomModeWindow());
        HBox modeBox = new HBox(10, dayModeBtn, nightModeBtn, ecoModeBtn, vacationModeBtn, customModeBtn);

        Label tempLabel = createStyledLabel("Temperature Control:", "#7e57c2");
        ComboBox<Integer> tempSelector = new ComboBox<>();
        tempSelector.getItems().addAll(18, 20, 22, 24, 26);
        tempSelector.setValue(22);
        Button setTempBtn = createStyledButton("Set Temperature", "#d1c4e9", e -> {
            int temp = tempSelector.getValue();
            thermostat.setTemperature(temp);
            activityLog.appendText("Temperature set to " + temp + "°C\n");
        });
        HBox tempBox = new HBox(10, tempSelector, setTempBtn);

        Label lightLabel = createStyledLabel("Light Controls:", "#7e57c2");
        Button lightOnBtn = createStyledButton("Turn Light ON", "#d1c4e9", e -> {
            remote.setCommand(new LightOnCommand(light));
            remote.pressButton();
            activityLog.appendText("Light turned ON\n");
        });

        Button lightOffBtn = createStyledButton("Turn Light OFF", "#d1c4e9", e -> {
            remote.setCommand(new LightOffCommand(light));
            remote.pressButton();
            activityLog.appendText("Light turned OFF\n");
        });

        HBox lightBox = new HBox(10, lightOnBtn, lightOffBtn);

        Label motionLabel = createStyledLabel("Motion Sensor Control:", "#7e57c2");
        Button motionDetectBtn = createStyledButton("Detect Motion", "#d1c4e9", e -> {
            motionSensorLight.detectMotion();
            activityLog.appendText("Motion Sensor Activated\n");
        });
        HBox motionBox = new HBox(10, motionDetectBtn);

        Label dimLabel = createStyledLabel("Dimmable Light Control:", "#7e57c2");
        Slider brightnessSlider = new Slider(0, 100, 100);
        brightnessSlider.setShowTickLabels(true);
        brightnessSlider.setShowTickMarks(true);

        Button dimLightBtn = createStyledButton("Set Brightness", "#d1c4e9", e -> {
            int brightness = (int) brightnessSlider.getValue();
            dimmableLight.dim(brightness);
            activityLog.appendText("Light brightness set to " + dimmableLight.getBrightness() + "%\n");
        });
        HBox dimBox = new HBox(10, new Label("Brightness:"), brightnessSlider, dimLightBtn);

        Button lockDoorBtn = createStyledButton("Lock Door", "#d1c4e9", e -> {
            remote.setCommand(new LockDoorCommand(doorLock));
            remote.pressButton();
            activityLog.appendText("Door LOCKED\n");
        });

        Button unlockDoorBtn = createStyledButton("Unlock Door", "#d1c4e9", e -> {
            remote.setCommand(new UnlockDoorCommand(doorLock));
            remote.pressButton();
            activityLog.appendText("Door UNLOCKED\n");
        });
        HBox doorBox = new HBox(10, lockDoorBtn, unlockDoorBtn);

        Button displayComponentsBtn = createStyledButton("Show Devices", "#b39ddb", e -> displayAllComponents());
        HBox componentBox = new HBox(10, displayComponentsBtn);


        Label logLabel = createStyledLabel("Activity Log:", "#7e57c2");
        activityLog.setEditable(false);
        activityLog.setPrefHeight(300);

        root.getChildren().addAll(
                roomBox, modeLabel, modeBox,
                tempLabel, tempBox,
                lightLabel, lightBox,
                doorBox,
                motionLabel, motionBox,
                dimLabel, dimBox,
                logLabel, activityLog
        );


        return root;
    }

    private void switchRoom() {
        switch (roomSelector.getValue()) {
            case "Living Room":
                currentRoom = livingRoom;
                break;
            case "Kitchen":
                currentRoom = kitchen;
                break;
            case "Bedroom1":
                currentRoom = bedroom1;
                break;
            case "Bedroom2":
                currentRoom = bedroom2;
                break;
            case "Bathroom":
                currentRoom = bathroom;
                break;
        }

        for (Component component : currentRoom.getComponents()) {
            if (component instanceof Leaf) {
                Leaf leaf = (Leaf) component;
                if (leaf.getDevice() instanceof Light) {
                    light = (Light) leaf.getDevice();
                    motionSensorLight = new MotionSensorDecorator(light);
                    dimmableLight = new DimmableLightDecorator(light);
                } else if (leaf.getDevice() instanceof Thermostat) {
                    thermostat = (Thermostat) leaf.getDevice();
                } else if (leaf.getDevice() instanceof DoorLock) {
                    doorLock = (DoorLock) leaf.getDevice();
                }
            }
        }
        activityLog.appendText("Switched to " + currentRoom.getName() + "\n");
    }



    private void displayAllComponents() {
        activityLog.appendText("Devices in " + currentRoom.getName() + ":\n");
        for (Component component : currentRoom.getComponents()) {
            if (component instanceof Leaf) {
                Leaf leaf = (Leaf) component;
                activityLog.appendText("- " + leaf.getName() + "\n");
            } else if (component instanceof Composite) {
                Composite composite = (Composite) component;
                activityLog.appendText("- Composite: " + composite.getName() + "\n");
            }
        }
    }




private Button createStyledButton(String text, String color, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: black;");
        button.setOnAction(action);
        return button;
    }

    private Label createStyledLabel(String text, String color) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 18));
        label.setTextFill(Color.web(color));
        return label;
    }

    private void activateMode(ModeStrategy mode) {
        this.currentMode = mode;
        for (Component component : currentRoom.getComponents()) {
            if (component instanceof Leaf) {
                Leaf leaf = (Leaf) component;
                if (leaf.getDevice() instanceof Light) {
                    mode.applyMode((Light) leaf.getDevice(), thermostat, doorLock);
                }
            }
        }
        activityLog.appendText(currentMode.getModeName() + " Activated in " + currentRoom.getName() + "\n");
    }

    private void openCustomModeWindow() {
        Stage customStage = new Stage();
        customStage.setTitle("Custom Mode Settings");

        CheckBox lightCheck = new CheckBox("Light ON");
        CheckBox doorLockCheck = new CheckBox("Door LOCKED");
        Slider brightnessSlider = new Slider(0, 100, 50);
        brightnessSlider.setShowTickLabels(true);
        brightnessSlider.setShowTickMarks(true);

        TextField tempField = new TextField();
        tempField.setPromptText("Enter Temperature");

        Button applyBtn = new Button("Apply Custom Mode");
        applyBtn.setOnAction(e -> {
            try {
                int temp = Integer.parseInt(tempField.getText());
                boolean lightState = lightCheck.isSelected();
                boolean doorLocked = doorLockCheck.isSelected();
                int brightness = (int) brightnessSlider.getValue();

                activateMode(new CustomMode(temp, lightState, doorLocked));
                activityLog.appendText("Custom Mode Applied: Temp " + temp + "°C, Light: " + (lightState ? "ON" : "OFF") + ", Brightness: " + brightness + "%, Door: " + (doorLocked ? "LOCKED" : "UNLOCKED") + "\n");
                customStage.close();
            } catch (NumberFormatException ex) {
                activityLog.appendText("Invalid Temperature Value\n");
            }
        });

        VBox layout = new VBox(10, lightCheck, doorLockCheck, new Label("Brightness:"), brightnessSlider, tempField, applyBtn);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 350);
        customStage.setScene(scene);
        customStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
