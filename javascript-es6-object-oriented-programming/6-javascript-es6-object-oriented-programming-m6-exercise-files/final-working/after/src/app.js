import $ from 'jquery';
import {Car} from './classes/car.js';
import {Drone} from './classes/drone.js';
import {fleet} from './fleet-data.js';
import {FleetDataService} from './services/fleet-data-service.js';
import {Button} from './ui/button.js';
import {Image} from './ui/image.js';
import {TitleBar} from './ui/title-bar.js';

import {ApplicationBase} from './framework/application-base.js';
import {HomePage} from './home-page.js';
import {CarsPage} from './cars-page.js';
import {MapPage} from './map-page.js';

export class App extends ApplicationBase {
    
    constructor() {
        super('Fleet Manager');
        
        this.dataService = new FleetDataService();
        this.dataService.loadData(fleet);
        
        this.addRoute('Home', new HomePage(), true);
        this.addRoute('Cars', new CarsPage());
        //this.addRoute('Drones', 'drones');
        this.addRoute('Map', new MapPage());
    }
}


// window.app = new App();
// window.app.show($('body'));

export let application = new App();
application.show($('body'));

