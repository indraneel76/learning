import {Component} from '@angular/core' ;
import {Employee} from './models/employee';

@Component({

  selector: 'app-root',
  //template: '<h1> Hello {{name}}</h1>'
  templateUrl: './home.html'
})

export class AppComponent {

  name: string ='Angular Forms. ' ;
  languages=['English','German','Spanish','Other'];
  employee = new Employee('Indraneel','Ganguli',true,'Monthly','default');
  
  hasPrimaryLangaugeError=false;
  validatePrimaryLanguage(value:string)
  {
    console.log('lang: ' +this.employee.primaryLanguage);
    if(value === 'default')
    {
      this.hasPrimaryLangaugeError=true;
    }
    else
    {
      this.hasPrimaryLangaugeError=false;
    }
  }



  firstnameuppercase(value: string)
  {
    if (value.length>0)
    {
      this.employee.firstname=value.charAt(0).toUpperCase()+value.slice(1);
    }
    else{
      this.employee.firstname=value;
    }
  }

}


