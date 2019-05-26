import {Car} from '../classes/car.js';
import {Drone} from '../classes/drone.js';
import {DataError} from './data-error.js';

export class FleetDataService {

    constructor() {
        this.cars = [];
        this.drones = []; 
        this.errors = [];       
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
                default:
                    let e = new DataError('Invalid vehicle type', data);
                    this.errors.push(e);
                    break;
            }
        }
    }
    
    loadCar(car) {
        try {
            let c = new Car(car.license, car.model, car.latLong);
            c.miles = car.miles;
            c.make = car.make;
            return c;
        } catch(e) {
            this.errors.push(new DataError('error loading car', car));
        }
        return null;
    }
}