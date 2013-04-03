define(
function(){
function changeColor(selection_fn, color){
  return function(d) {
    var selection_str=selection_fn(d);
    var e=d3.select(selection_str);    
    e.transition().style("fill", color);
//      alert(ev.date);
    }
};
function  lib_func(selection_fn, color, color2){
  return this.on("mouseover", changeColor(selection_fn, color2))
    .on("mouseout", changeColor(selection_fn,color));
}

function selectingRectById(d /**d= each row in d3.data(_data)**/){
   return  "svg #"+format(d,["month", "year"],"_");
  }
  return {lib_func:lib_func, selectingRectById:selectingRectById, changeColor:changeColor};
  
});;

