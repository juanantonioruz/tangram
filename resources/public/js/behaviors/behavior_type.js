define(["js/jquery-1.9.1.min.js"], function() {
    var Behavior=function(name){
        this.data={};
        this.data.ns=name;
        this.on_start=[];
    };  


    Behavior.prototype.message=function(semantic_element, message){
        $(semantic_element).html(message);
    };

    Behavior.prototype.behavior=function(event_data, callback){
        // console.log( this.data.ns);
        //console.log();

        callback(null, event_data);
    };

    Behavior.prototype.process=function(event_data, callback){
        var that=this;
        event_data.event_history.push(this.data.ns);
        $('#status').html("init: "+this.data.ns);
        (function(){
            that.behavior(event_data, callback);

        })();
    };

    var B= function(name){return new Behavior(name);};
    return B;
});
