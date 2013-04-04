/**
   helper_view.js
   
   model(dates and birds).js related to d3.js/model

   this lib adds or removes properties of the model inside model.js for
   adapting to d3.js model, that is, adapting to d3js API so this
   library for making visualizations can use this data 



**/

function adaptToTreeData(inmutable_dataArray){
  return inmutable_dataArray
    .map(
      function(o){
        // explaining why I've applied here prototypical inheritance
        //  just to clone the object using _prototype property
        // for this purposes always thinking in not alter the base object
        var f=Object.create(o);
        f.contents=[];
        return f;
      }
    );
};


define(
  function(){
    return {adaptToTreeData:adaptToTreeData};
  }
 
)

