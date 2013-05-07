define([ "js/behaviors/behavior_type.js","js/jquery-1.9.1.min.js"], function(B) {
 
    var load_history=B("load_history");
    
    load_history.behavior=function(event_data, callback){
        this.message("#status", "loading user history");
        setTimeout(function () {
            var user_history=[];
            user_history.push("click here");
            user_history.push("click there");
            user_history.push("click here again");
            user_history.push("click there again");
            event_data.user_history=user_history;
            callback(null, event_data);
        }, 1000);
        
    };

    var show_user_history=B("show_user_history");
    
    show_user_history.behavior=function(event_data, callback){
        setTimeout(function () {
 // TODO  change selector by semantic id
          $('#content').append("<h1>user_history</h1><ul></ul>");
            $.each(event_data.user_history, function(i, value){
                $('#content ul').append("<li>"+value+"</li>");
            });
        
            callback(null, event_data);
        }, 1000);

    };

    var show_history=B("show_history");
    
    show_history.behavior=function(event_data, callback){
 setTimeout(function () {
          $('#event_history').append(event_data.template);
            $.each(event_data.event_history, function(i, value){
                $('#event_history div ul').append("<li>"+value+"</li>");
            });
            // $('#event_history div ul').after("<h2>"+event_data.template_message+"</h2>");
            callback(null, event_data);
        }, 1000);
    };


    var template_history=B("template_history");

    template_history.behavior=function(event_data, callback){
        this.message("#status", "loading dynamic_template");
        setTimeout(function () {
            event_data.template_message="the template is dynamic and now is displayed!";
            event_data.template="<div style='background-color:green;'><h1>Behavior history</h1><ul></ul></div>";
            
            callback(null, event_data);
        }, 1000);
    };
   
    var behaviors_map={};
    behaviors_map[load_history.data.ns]=load_history;
    behaviors_map[show_history.data.ns]=show_history;
    behaviors_map[template_history.data.ns]=template_history;
    behaviors_map[show_user_history.data.ns]=show_user_history;
    

    return behaviors_map;
});
