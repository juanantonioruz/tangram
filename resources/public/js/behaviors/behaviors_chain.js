define(function(){
    return function get_chain(_arr){
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
};});
