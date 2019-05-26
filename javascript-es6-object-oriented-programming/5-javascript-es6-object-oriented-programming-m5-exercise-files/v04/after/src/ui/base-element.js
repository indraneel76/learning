import $ from 'jquery';

export class BaseElement {
    
    constructor() {
        this.element = null;  // jQuery object
    }
    
    appendToElement(el) {
        this.createElement();
        el.append(this.element);
    }
    
    createElement() {
        let s = this.getElementString();
        this.element = $(s);
    }
    
    getElementString() {
        throw 'Please override getElementString() in BaseElement';
    }
}