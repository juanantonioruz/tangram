define(
function(){
function changeColor(selection_fn, color){
  return function(d) {

    var selection_str=selection_fn(d);
    var e=d3.select(selection_str);    
    e.transition().duration(Math.random()*2*100).style("fill", color);
//      alert(ev.date);
    }
};
function  mouseOverOutChangeColor(selection_fn, color, color2){


  return this.on("mouseover", changeColor(selection_fn, color2))
    .on("mouseout", changeColor(selection_fn,color));
}

function selectingRectById(d /**d= each row in d3.data(_data)**/){
   return  "svg #"+format(d,["month", "year"],"_");
  }



function addMethodToSelection(selection, method){
  selection.changeColorRectRelatedOnMouseEvents=method;
  return selection;
}

  return {mouseOverOutChangeColor:mouseOverOutChangeColor, selectingRectById:selectingRectById, changeColor:changeColor,  addMethodToSelection:addMethodToSelection};
  
});;

