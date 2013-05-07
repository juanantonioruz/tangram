require(["js/behaviors/compose_async.js", "js/behaviors/behaviors_service.js","js/jquery-1.9.1.min.js"], function(compose, BS) {


    var behaviors_array=[
        BS.load_history, 
        BS.show_user_history, 
        BS.show_history];

     //TODO: it must be done in any place in code, currently only works before compose_behaviors invocation Behavior manipulation AOP
    BS.show_history.on_start.push(BS.template_history);


    var event_data={current_context:"here!",event_history:[]};

    var compose_behaviors=compose(behaviors_array);

    compose_behaviors(event_data, function (err, result) {
        if(err) console.dir(err);
        $(function(){
          
            $('#status').html("end all behaviors! ").css('background-color', 'yellow').fadeOut(1000);            
        });
        console.log(toJson(result));
    });




});
