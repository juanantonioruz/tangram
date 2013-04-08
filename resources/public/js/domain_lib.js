define( ["./ddd_lib"],
        function(ddd_lib){


          function MonthDisplayable(){
            this.d3_g=null;
            this.display_data=[];
            this.display_data_pos=0;
            this.display_increment=1;
}
          

          MonthDisplayable.prototype.selectedMonth=null;
          
          MonthDisplayable.prototype.setM=function(v){
            MonthDisplayable.prototype.selectedMonth=v;
          };
          
          /**
           //check if is the same node clicked twice times // in this case hide element
           **/
          MonthDisplayable.prototype.check=function(selectedNode){

            if(this.selectedMonth==null){
                this.setM(selectedNode);
              
            }else{
              if( this.selectedMonth.attr("id")!=selectedNode.attr("id")){
               this.monthListenClick(this.selectedMonth);
                this.selectedMonth.selectAll("rect.menu").remove();
                this.setM(selectedNode);
              
              }else{
                this.setM(null);
              }
            }

          }

          MonthDisplayable.prototype.monthListenClick=function (selection){
           
            selection
            // moving up 
              .transition()
              .duration(350)
              .attr("transform", ddd_lib.translate(selection.datum().m))
            // moving down and scaling
              .each("end", function(){
                console.log("first!")
            }
                  )
              .transition()
              .duration(250)
              .attr("transform", ddd_lib.translate(selection.datum().m))
              .each("end", function(){
                console.log("end second transition! ")
              }
                  );
              

          };

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

          return { MonthDisplayable:MonthDisplayable, changeColorRectRelatedOnMouseEvents:changeColorRectRelatedOnMouseEvents, format:format, val:val};
        });;







