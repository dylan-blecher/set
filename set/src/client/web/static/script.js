// Establishing connection with the server hosted at domain:port
// var socket = io.connect('http://127.0.0.1:5000/');
// var socket = io();
// socket.on('some event', function(msg) {
//     console.log(msg);
// });


// console.log("fuck asdfasdf")
// var socket = io();
// socket.on('connect', function() {
//     console.log("fuck ye")
//     socket.emit('my event', {data: 'I\'m connected!'});
//     console.log("fuck ye2")
// });

// Listening for event named `any event`

// // Client Side Javascript to receive numbers.
$(document).ready(function() {
    var socket = io.connect('http://' + document.domain + ':' + location.port);
    // socket.emit('message', {data: "wattup"});
    // socket.emit({data: "wattup"});
    // var myObj = {
    //  data: "wattup"
    // };
    // socket.send(myObj);
    socket.send("db ayay");
    // socket.send({data: "wattup"});
    console.log("GOT HERE")
    var cardsSelected = [];
    
    var cards = document.getElementsByClassName("card");
    for (card of cards) {
        card.addEventListener("click", selectCard);
        card.addEventListener("mouseover", highlightCard);
        card.addEventListener("mouseout", unhighlightCard);
    }

    var revealSetButton = document.getElementById("revealSet");
    revealSetButton.addEventListener("click", revealSet);

    var drawCardsButton = document.getElementById("drawCards");
    drawCardsButton.addEventListener("click", drawCards);

    function revealSet() {
        console.log("CLICK REGISTERED!")
        socket.emit('reveal_set');
    }

    function drawCards() {
        console.log("CLICK REGISTERED!")
        socket.emit('draw_cards');
    }

    function selectCard(event) {
        // user has clicked on a card
        // get the card, not the shape (.target would get the child)
        var cardElClicked = event.currentTarget; 
        if (cardsSelected.includes(cardElClicked)) {
            // card has already been selected so deselect
            cardElClicked.classList.add('hoverOverCard');
            cardElClicked.classList.remove('selectCard');

            // remove element
            const index = cardsSelected.indexOf(cardElClicked);
            if (index > -1) {
                cardsSelected.splice(index, 1);
            }
        } else {
            // card hasn't been selected so select it
            cardElClicked.classList.add('selectCard');
            cardElClicked.classList.remove('hoverOverCard');

            cardsSelected.push(cardElClicked);
            
            if (cardsSelected.length == 3) {
                console.log(cardsSelected);
                cardIDs = [];
                for (cardEl of cardsSelected) {
                    cardIDs.push(parseInt(cardEl.id));
                    cardEl.classList.remove('selectCard');

                }
                // socket.send("assss");
                console.log(cardIDs);
                socket.emit('select_set', cardIDs);

                // var myObj = {
                //  data: "wattup"
                // };
                // socket.send(myObj);
                // socket.emit({data: "wattup"});
                // socket.send({data: "wattup"});
                cardsSelected = [];
            }
        }
    }

    function highlightCard(event) {
        var cardElClicked = event.currentTarget; 
        cardElClicked.classList.add('hoverOverCard');
    }

    function unhighlightCard(event) {
        var cardElClicked = event.currentTarget; 
        cardElClicked.classList.remove('hoverOverCard');
    }

    socket.on('board_change', function (msg) {
        var state = JSON.parse(msg)
        // console.log(state.board)  
        for (cardID in state.board) {
            console.log(state)
            var card = state.board[cardID]
            // console.log(card)
            var cardEl = document.getElementById(cardID);
            // would be more efficient to only do the following if the card has changed which I can check by
            // looking into the HTML
            cardEl.innerHTML = '';
            if (! card.hasOwnProperty('colour') || 
                ! card.hasOwnProperty('fill') || 
                ! card.hasOwnProperty('number') || 
                ! card.hasOwnProperty('shape')) {
                
                continue;
            }

            var shape = document.createElement("div");
            shape.classList.add(card.colour, card.fill, card.shape);
            // shape.classList.add(card.colour.toLowerCase(), card.fill.toLowerCase(), card.shape.toLowerCase());
            


            var dict = {
                "ONE": 1,
                "TWO": 2,
                "THREE": 3
            };

            var nShapes = dict[card.number];
            for (var i = 0; i < nShapes; i++) {
                console.log("nshapes of cardID" + cardID + " is " + nShapes)
                cardEl.appendChild(shape.cloneNode(true));
            }
        }
  });

    
});
//     // start up the SocketIO connection to the server - the namespace 'test' is also included here if necessary
//     console.log('http://' + document.domain + ':' + location.port + '/test')
    // var socket = io.connect('http://' + document.domain + ':' + location.port + '/test');
//  // var socket = io.connect('http://localhost:5000');
// });
//     // this is a callback that triggers when the "my response" event is emitted by the server.
//     socket.on('my response', function(msg) {
//      console.log(msg.data);
//         $('#log').append('<p>Received: ' + msg.data + '</p>');
//     });
//     //example of triggering an event on click of a form submit button
//     $('form#emit').submit(function(event) {
//         socket.emit('my event', {data: $('#emit_data').val()});
//         return false;
//     });
// });

// $(function(){
//  $('button').click(function(){
//      var user = $('#inputUsername').val();
//      var pass = $('#inputPassword').val();
//      $.ajax({
//          url: '/signUpUser',
//          data: $('form').serialize(),
//          type: 'POST',
//          success: function(response){
//              console.log(response);
//          },
//          error: function(error){
//              console.log(error);
//          }
//      });
//  });
// });

// console.log(jQuery.getJSON('/signUpUser'))