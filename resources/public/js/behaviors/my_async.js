var Behavior=function(event_data){
    this.data=event_data;

    this.onStartChain=[];
    this.onEndChain=[];

};  



var myF=function(item, callback){
    setTimeout(function(){
        callback(null, item.data);
    }, 500);
//    return this.data;
};

var myF2=function(item, callback){
    setTimeout(function(){
        callback(null, "eyyy: "+item.data);
    }, 500);
//    return this.data;
};
Behavior.prototype.process=myF;



// function asyncProcess(value, callback){
//   var new_value=value+1;
//   return callback(null, new_value);
// };

var one=new Behavior("one");
var B= function(name){return new Behavior(name);};
var B_A=B("A");
B_A.onStartChain.push(B("1"), B("2"));

one.onStartChain.push( B_A,B("B"), B("C"));

var myB=B("mine");
myB.onStartChain.push(one, B("two"), B("three"));


var array_walk=[];

function recursive_chain(behavior){
    for(var i =0;i<behavior.onStartChain.length; i++){
        var be_int=behavior.onStartChain[i];
        array_walk.push(be_int);
        recursive_chain(be_int);
    }
}
// to obtain the recursive call path
recursive_chain(myB);

async.map(
    array_walk,
   myF
    , function(err, results){


        if(err)
            console.log("!:(");
        else{
            console.log("!:) "+results);
           
        }

async.map(
    array_walk,
   myF2
    , function(err, results){
        if(err)
            console.log("!:(");
        else{
            $('#content').html(results);
            console.log("!:) "+results);
            
        }
});


});

