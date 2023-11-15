# CarEvolution

## Project Overview

This project is a simulation based on the concept of neuroevolution, a machine-learning technique that utilizes neural networks, weights, and nodes to train cars to navigate a specific race track. The goal is to demonstrate that machine learning concepts can be accessible and enjoyable. The application allows users to interact with various options, giving them control over the neural network parameters. Users can create custom maps, adjust the number of cars, mutation rate, car speed, angular velocity, and the neural network's hidden layers and nodes.

### Neural Network Architecture

The neural network is the core of the application. Each car is equipped with seven sensors that measure the distance from the car to a wall. These sensor inputs pass through the neural network, consisting of layers and nodes with weights generated between 0.0 and 1.0. The neural network's output determines the angular velocity, influencing how cars turn and in which direction.

### Fitness Score Description

The fitness score is calculated based on the distance traveled by each car. Instead of using a fixed map, users generate maps by clicking on the screen. Each click becomes a checkpoint for the fitness score calculation, providing a more accurate evaluation of the cars' performance.

### Implementation

#### Implemented Features

1. Starting and Pausing Cars
   - Enables users to pause and play the animation, facilitating observation of specific cars.

2. Configuring Neural Network
   - Users can customize the number of layers and the mutation rate.
![neural network layers customization](images/neuralNetworkView.gif)

1. Configuring Cars
   - Allows customization of the number of cars, car speed, and angular velocity.

2. Makes Map
   - Allows users to make their own map.
![map layers customization](images/makeMap.gif)

3. Settings Customization
   - Enable the user to change the settings of the car to its preferences.
![settings customization](images/buttonChange.gif)

#### Major Features

##### Neural Network

Initially implemented using linear algebra, the neural network was later separated into weights and neurons matrices. This design facilitates customizability. The weights matrix is automatically generated based on the size of the neurons matrix, allowing easier adjustments to the number of layers and neurons.

##### Car's Sensors

Each car has seven sensors implemented as JavaFX Line shapes. Collision detection is performed by checking intersections with walls, and sensor distances are used as inputs for the neural network.
![cars learning](images/carsLearning.gif)

##### Fitness Score

Changed from frame-rate-based scoring to user-generated maps. Clicking on the screen creates checkpoints for fitness score calculation, providing a more accurate evaluation.

##### Neural Network Mutation

To evolve, cars are trained through cloning the weights of the best two cars and introducing random mutations based on a user-adjustable learning rate.

### Our Acquired Knowledge

This project enhanced understanding of neuroevolution, teamwork, and GitHub collaboration. Technical challenges included refining the fitness score calculation and addressing issues like cars rotating endlessly. The team overcame these challenges through effective communication and collaboration.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Installation

Compile the JAR file using an IDE.

## License

[MIT](https://choosealicense.com/licenses/mit/)
