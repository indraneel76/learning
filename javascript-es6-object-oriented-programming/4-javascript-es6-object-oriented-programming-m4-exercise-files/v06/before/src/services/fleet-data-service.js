import {Car} from '../classes/car.js';
import {Drone} from '../classes/drone.js';

export class FleetDataService {

    constructor() {
        this.cars = [];
        this.drones = [];        
    }    
    
    loadData(fleet) {
        for (let data of fleet) {
            switch(data.type) {
                case 'car':
                    let car = this.loadCar(data);
                    this.cars.push(car);
                    break;
                case 'drone':
                    this.drones.push(data);
                    break;
            }
        }
    }
    
    loadCar(car) {
        let c = new Car(car.license, car.model, car.latLong);
        c.miles = car.miles;
        c.make = car.make;
        return c;
    }
}