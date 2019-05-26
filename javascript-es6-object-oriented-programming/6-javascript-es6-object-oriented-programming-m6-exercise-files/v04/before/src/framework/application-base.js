import {TitleBar} from '../ui/title-bar.js';

export class ApplicationBase {
    
    constructor(title) {
        this.title = title;
        this.titleBar = new TitleBar(this.title);
        this.routeMap = {};
        this.defaultRoute = null;
    }
    
    addRoute(id, pageObject, defaultRoute = false) {
        this.titleBar.addLink(id, '');

        this.routeMap[id] = pageObject;
        
        if (defaultRoute) {
            this.defaultRoute = id;
        }
    }
    
    show(element) {
        this.titleBar.appendToElement(element);
    }
}