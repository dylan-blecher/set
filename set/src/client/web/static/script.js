// Establishing connection with the server hosted at domain:port
const SET_DISPLAY_TIME = 3000

// // Client Side Javascript to receive numbers.
$(document).ready(function() {
    var socket = io.connect('http://' + document.domain + ':' + location.port);
    
    var cardsSelected = [];

    var revealSetButton = document.getElementById("revealSet");
    revealSetButton.addEventListener("click", revealSet);

    var drawCardsButton = document.getElementById("drawCards");
    drawCardsButton.addEventListener("click", drawCards);

    function revealSet() {
        socket.emit('reveal_set');
    }

    function drawCards() {
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
                cardPositions = [];
                for (cardEl of cardsSelected) {
                    cardPositions.push(parseInt(cardEl.id));
                    cardEl.classList.remove('selectCard');
                }
                socket.emit('select_set', cardPositions);

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
        console.log("got board_change")
        var state = JSON.parse(msg);
        for (cardPosition in state.board) {
            var card = state.board[cardPosition];
            var cardEl = document.getElementById(cardPosition);
            // would be more efficient to only do the following if the card has changed which I can check by
            // looking into the HTML
            cardEl.innerHTML = '';
            if (! card.hasOwnProperty('colour') || 
                ! card.hasOwnProperty('fill') || 
                ! card.hasOwnProperty('number') || 
                ! card.hasOwnProperty('shape')) {
                
                // keep an empty div in empty card elements so formatting is consistent
                // with non-empty card elements :)
                // var emptyShape = document.createElement("div");
                // emptyShape.classList.add("SQUIGGLE", "RED", "OPEN")
                // cardEl.appendChild(emptyShape);
                cardEl.classList.remove('cardShadow');
                
                cardEl.removeEventListener("click", selectCard);
                cardEl.removeEventListener("mouseover", highlightCard);
                cardEl.removeEventListener("mouseout", unhighlightCard);

                continue;
            }

            cardEl.classList.add('cardShadow');
            cardEl.addEventListener("click", selectCard);
            cardEl.addEventListener("mouseover", highlightCard);
            cardEl.addEventListener("mouseout", unhighlightCard);

            var shapeEl = document.createElement("div");
            shapeEl.classList.add(card.colour, card.fill, card.shape);
            // shape.classList.add(card.colour.toLowerCase(), card.fill.toLowerCase(), card.shape.toLowerCase());

            var dict = {
                "ONE": 1,
                "TWO": 2,
                "THREE": 3
            };

            var nShapes = dict[card.number];
            for (var i = 0; i < nShapes; i++) {
                cardEl.appendChild(shapeEl.cloneNode(true));
            }
        }
    });

    socket.on('reveal_set', function (msg) {
        var revealedSet = JSON.parse(msg);
        console.log("got revealed set")

        for (cardPosition of revealedSet.cardPositions) {
            var cardEl = document.getElementById(cardPosition);
            // disable players ability to make a move while revealing a set

            // highlight card
            cardEl.classList.add('revealCard')
        }

        setTimeout(
            function() {
                for (cardPosition of revealedSet.cardPositions) { 
                    var cardEl = document.getElementById(cardPosition);
                    cardEl.classList.remove('revealCard') 
                }
            }, 
            SET_DISPLAY_TIME
        );

    });
    
});