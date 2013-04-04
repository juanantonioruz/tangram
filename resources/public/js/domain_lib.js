define( ["./ddd_lib"],
        function(ddd_lib){

          function selectingRectById(d /**d= each row in d3.data(_data)**/){
            return  "svg #"+format(d,["month", "year"],"_");
          }

          function  changeColorRectRelatedOnMouseEvents(selection,  color, color2){
            return ddd_lib.mouseOverOutChangeColor.call(selection, selectingRectById, color,color2)
          };

          function format(d, keyArray, join){
            var m=keyArray.map(function(o){return d[o]});
            var h=m.join(join);
            return "_"+h;
          }

          function val(keyArray, join){
            return function(d){
              return format(d,keyArray,join);
            };
          }          

          return { changeColorRectRelatedOnMouseEvents:changeColorRectRelatedOnMouseEvents, format:format, val:val};
        });;







