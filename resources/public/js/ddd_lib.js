define(
  function(){
    /*spec: {margin:100, margin_inside:50, width:w, collection:x}*/
        function HorizontalLayout(spec){
          this.margin=spec.margin;        
          this.elements_size=spec.collection.length;
          this.margin_inside=spec.margin_inside;
          this.width_available=spec.width-(this.margin*2)-(this.margin*(this.elements_size-1));
          this.element_width=this.width_available/this.elements_size;
          
         this.getElementWidth=(function(t){
           var that=t;
           return function(){
            return that.element_width;
           }
         })(this);

        this.getX=(function(t){
          var that=t;
         return  function(d,i){
            var space=0;
            for(var j=0;j<i;j++)
              space+=that.element_width+that.margin_inside;
            return that.margin+space;
          };
        })(this);
          
          
        };
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

    function translate(data_displayable){

      var pos=data_displayable.display_data_pos;
      var length=data_displayable.display_data.length;
      var n_pos=pos%length;
      if(n_pos==(length-1)){
        data_displayable.display_increment=-1;
      }else if(n_pos ==0){
        data_displayable.display_increment=1;
      }

      var the_display_data=data_displayable.display_data[n_pos];      
      var res="translate("+the_display_data.x+","+the_display_data.y+") scale("+the_display_data.scale+")";

      data_displayable.display_data_pos=data_displayable.display_data_pos+data_displayable.display_increment;

      return res;
    };



    return {
      mouseOverOutChangeColor:mouseOverOutChangeColor, HorizontalLayout:HorizontalLayout,
      changeColor:changeColor, random:random, log:log, translate:translate};
    
  });;

