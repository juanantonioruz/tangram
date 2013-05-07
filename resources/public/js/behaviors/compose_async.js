define(["js/async.js", "./behaviors_chain.js"], function(async, chain) {
    
    return function composeAsyncBehaviors(the_behaviors){
        return  async.compose.apply(null, chain( the_behaviors.reverse()));
    };

        
    }
);
