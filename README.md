# Tetris Game

A Tetris game implemented in Java using the Model-View-Controller (MVC) design pattern.
Made by Arthur RIGONNET and Mathieu PONTON under the supervision of Mr. Frederic Armetta during our 3rd Year at Polytech Lyon.

## Table of Contents

- [Getting Started](#getting-started)
- [Gameplay](#gameplay)
- [Code Structure](#code-structure)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

To get a local copy up and running, follow these steps:

1. Clone the repo
```sh
git clone https://github.com/TaTu03TV/Java-Tetris-MVC.git
```
2. Open the project in your preferred Java IDE (e.g., VsCode, Eclipse, Neovim)

3. Run the [`Main.java`] file to start the game

## Gameplay

The game is controlled using the keyboard. The current controls are:

- 'ZQSD' to move the piece
- Space to hard drop
- 'h' to hold a piece
- 'p' to pause the game

The game features a scoring system, where completing more lines at once results in more points, and a leveling system, each level being harder but rewarding more points.

## Code Structure

The code is organized following the Model-View-Controller (MVC) design pattern. The game's logic is located in the `models`package, the graphical user interface in the `views` package, and the controller in the `controllers` package.

The game's assets, such as fonts and sound effects, are located in the `assets`directory.

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.
