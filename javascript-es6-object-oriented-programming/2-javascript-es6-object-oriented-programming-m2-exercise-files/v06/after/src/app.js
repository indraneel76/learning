class Drone {
    constructor(id, name) {
        this.id = id;
        this.name = name;
    }
}
Drone.maxHeight = 2000;

let drone = new Drone('A123', 'Flyer');
let drone2 = new Drone('B456', 'Twirl');

console.log(drone.maxHeight);
