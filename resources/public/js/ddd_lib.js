define(
  function(){
    function log(e){
      var v=JSON.stringify(e, null, 4);
      console.log(v);
      return v;
    }

    function random(v){
      return Math.random()*v;
    };

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
    };

    return {
      mouseOverOutChangeColor:mouseOverOutChangeColor, 
      changeColor:changeColor, random:random, log:log};
    
  });;

