class Vehicle {
    start() {
        console.log('staring Vehicle');
    }
    static getCompanyName() {
        console.log('My Company');
    }
}

class Car extends Vehicle {
    start() {
        super.start();
        console.log('staring Car');
    }
    static getCompanyName() {
        super.getCompanyName();
        console.log('My Other Company');
    }
}

let c = new Car();
Car.getCompanyName();
