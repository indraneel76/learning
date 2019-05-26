import {TitleBar} from '../ui/title-bar.js';

export class ApplicationBase {
    
    constructor(title) {
        this.title = title;
        this.titleBar = new TitleBar(this.title);
    }
    
    show(element) {
        this.titleBar.appendToElement(element);
    }
}