define( ["./ddd_lib"],
        function(ddd_lib){
          function  changeColorRectRelatedOnMouseEvents(color, color2){

            return ddd_lib.mouseOverOutChangeColor.call(this, ddd_lib.selectingRectById, color,color2)


          };
          return { changeColorRectRelatedOnMouseEvents:changeColorRectRelatedOnMouseEvents};
        });;







