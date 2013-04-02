function Bird(){
  this.name;
  this.refs={};
  
}
Bird.prototype.greeting=function(){
  return "Hi, I'm a bird "+this.name;
};

 var bird=new Bird();
  bird.name="Parrot";
  var toType = function(obj) {
    return ({}).toString.call(obj).match(/\s([a-zA-Z]+)/)[1].toLowerCase()
  }

  for(var prop in bird){

    // console.info(toType(bird[prop])+prop);

    
  }


function log(e){
  var v=JSON.stringify(e, null, 4);
     console.log(v);
return v;
}

function isTodayMonthYear(d){
  var today=new Date();
  return (d.getMonth() == today.getMonth() && d.getFullYear()==today.getFullYear());
};

function isTodayBeforeMonthYear(d){
  var today=new Date();
  return (d.getTime()<today.getTime());
};


var today=new Date();

var lastToday=new Date(today.getTime());

lastToday.setFullYear(today.getFullYear()-1);



today.setMonth(today.getMonth()+1);
log(lastToday+"--->"+today);

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
