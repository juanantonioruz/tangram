var Behavior=function(name){
   this.data={};
   this.data.ns=name;
   this.on_start=[];
};  

function toJson(o){
    return JSON.stringify(o, null, 4);
};

Behavior.prototype.behavior=function(event_data){
   // console.log( this.data.ns);
    //console.log();

    return event_data;
};

Behavior.prototype.process=function(event_data, callback){
    var that=this;
    event_data.event_history.push(this.data.ns);
    (function(){
             var result=that.behavior(event_data);
             callback(null, result);
    })();
};

var B= function(name){return new Behavior(name);};

var load_history=B("load History");


var show_history=B("show_history");

var template_history=B("get_template");

template_history.behavior=function(event_data){
    event_data=Behavior.prototype.behavior(event_data);
    event_data.template="the template is dynamic and now is loaded!";
    return event_data;
};

// behavior manipulation AOP
show_history.on_start.push(template_history);

var behaviors_array=[load_history, show_history].reverse();

function get_chain(_arr){
var acumulator=[];

function obtain_chain(arr){

    for(var i=0; i<arr.length; i++){
        var x=arr[i];
        acumulator.push(x.process.bind(x));        
       
        if(x.on_start.length>0)
            obtain_chain(x.on_start);
    }
}
obtain_chain(_arr);
return acumulator;
}
function composeAsyncBehaviors(the_behaviors){
    return  async.compose.apply(null, get_chain( the_behaviors));
}

var event_data={current_context:"here!",event_history:[]};

composeAsyncBehaviors(behaviors_array)(event_data, function (err, result) {
    if(err) console.dir(err);
        $(function(){
            $('#content').append("<h1>Behavior History</h1><ul></ul>");    
            $.each(result.event_history, function(i, value){
                $('#content ul').append("<li>"+value+"</li>");
            });
            $('#content ul').after("<h2>"+result.template+"</h2>");
            
        });

     console.log(toJson(result));
 });

















// an attemp to make behavior inheritance
// --------------------------------------------
// my_behavior2.prototype=my_behavior;

// my_behavior2.behavior=function(event_data, callback){
//     return "// "+my_behavior.behavior(event_data, callback)+"... ja..."+event_data+this.data+"---";
    
// };
