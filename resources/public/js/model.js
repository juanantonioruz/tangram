/**
   model.js
   is a conceptual mix for encapsulating (to create library as a namespace [the same that: group related functions with a prefix name])
   ... for encapsulating data model (db info) and query model (prepared statements)

   :)  I like that!  
**/


// example of model class
function Bird(){
  this.name;
  this.refs={};
  
}
Bird.prototype.greeting=function(){
  return "Hi, I'm a bird "+this.name;
};

// instance of class
var bird=new Bird();
bird.name="Parrot";


// trying to reflect into the object
var toType = function(obj) {
  return ({}).toString.call(obj).match(/\s([a-zA-Z]+)/)[1].toLowerCase()
}


for(var prop in bird){
  // console.info(toType(bird[prop])+prop);
}



// playing with dates! ... making dates as data to visualization 

var today=new Date();

var lastToday=new Date(today.getTime());

lastToday.setFullYear(today.getFullYear()-1);

today.setMonth(today.getMonth()+1);
//log(lastToday+"--->"+today);

var months=[];

while(today.getTime()!=lastToday.getTime()){
  //log(lastToday);
  var changed=new Date(lastToday);
  changed.setMonth(lastToday.getMonth()+1);
  months.push({id:1, month:changed.getMonth()+1, year:changed.getFullYear(), date:changed}
             );
  lastToday=changed;
}


//log(months);

function isTodayMonthYear(d){
  var today=new Date();
  return (d.getMonth() == today.getMonth() && d.getFullYear()==today.getFullYear());
};

function isTodayBeforeMonthYear(d){
  var today=new Date();
  return (d.getTime()<today.getTime());
};


function getDaysOfTheMonth(_date){
  var d_1=new Date(_date);

  d_1.setDate(1);

  var current_month=d_1.getMonth();
  var days=[];

  while(current_month==d_1.getMonth()){
    days.push(new Date(d_1.getTime()));    
    d_1.setDate(d_1.getDate()+1);

  }
  return days;
}


//console.log(getDaysOfTheMonth(new Date()));


define(
  function(){
    return {months:months,  isTodayMonthYear:isTodayMonthYear, isTodayBeforeMonthYear:isTodayBeforeMonthYear, getDaysOfTheMonth:getDaysOfTheMonth};
  }

  
)
