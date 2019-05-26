class Drone {
constructor(id,name){
    this._id=id;
    this.name=name;
    
}

get id1() {
    return this._id;
}

set id1(value) {

    this._id=value;
}
fly() {
    console.log('Drone '+this._id+ 'is flying');
}


}
let drone = new Drone('123','Hello');
console.log('drone id '+drone.id1);
drone.id1='456';
console.log('drone id '+drone.id1);