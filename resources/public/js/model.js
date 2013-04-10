          /**
             model.js
             is a conceptual mix for encapsulating (to create library as a namespace [the same that: group related functions with a prefix name])
             ... for encapsulating data model (db info) and query model (prepared statements)

             :)  I like that!  
          **/

define( ["./domain_lib"],
        function(domain_lib){


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

          function getMonths(startDate, endDate){
//            var lastToday=endDate;
             endDate.setMonth(endDate.getMonth()+1);
            // if(_startDate!==undefined){
            //   lastToday=_startDate;
              
            // }else{
             
            //   lastToday=new Date(endDate.getTime());
            //   lastToday.setFullYear(endDate.getFullYear()-1);
            // }

            //log(lastToday+"--->"+endDate);
            

            var months=[];
            while(endDate.getTime()>=startDate.getTime()){
              //log(lastToday);
              var changed=new Date(startDate);

              // the display_data property is designed to maintain states history
              // so you can return to a previous (origin) state... i think can be
              // interesting inside animations 
              months.push(
                {id:1, 
                 month:changed.getMonth()+1, 
                 year:changed.getFullYear(), 
                 date:changed, 
                 active:(Math.random() >= 0.5)

                }
              );
              changed.setMonth(startDate.getMonth()+1);
              startDate=changed;
            }
            return months;
            
          }
          


       



          //console.dir(months);

          function isTodayMonthYear(d){
            var endDate=new Date();
            return (d.getMonth() == endDate.getMonth() && d.getFullYear()==endDate.getFullYear());
          };

          function isTodayBeforeMonthYear(d){
            var endDate=new Date();
            return (d.getTime()<endDate.getTime());
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


          return {

            getMonths:getMonths,
            isTodayMonthYear:isTodayMonthYear,
            isTodayBeforeMonthYear:isTodayBeforeMonthYear,
            getDaysOfTheMonth:getDaysOfTheMonth};
        });

