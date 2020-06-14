// Establishing connection with the server hosted at domain:port
const SET_DISPLAY_TIME = 2500

$(document).ready(function() {
    var socket = io.connect('http://' + document.domain + ':' + location.port);
    
    var cardElsSelected = [];
    var cardElsHoveredOver = [];

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
        if (cardElsSelected.includes(cardElClicked)) {
            // card has already been selected so deselect
            addHover(cardElClicked)
            deselect(cardElClicked)
        } else {
            // card hasn't been selected so select it
            select(cardElClicked)
            removeHover(cardElClicked)
            
            if (cardElsSelected.length == 3) {
                cardPositions = [];
                for (cardEl of cardElsSelected) {
                    cardPositions.push(parseInt(cardEl.id));
                    cardEl.classList.remove('selectCard');
                }
                cardElsSelected = []
                socket.emit('select_set', cardPositions);
            }
        }
    }
    
    function addHover(cardEl) {
        cardEl.classList.add('hoverOverCard');
        cardElsHoveredOver.push(cardEl);
    }

    function removeHover(cardEl) {
        cardEl.classList.remove('hoverOverCard');
        
        // remove from cardEl cardElsHoveredOver list
        const index = cardElsHoveredOver.indexOf(cardEl);
        if (index > -1) {
            cardElsHoveredOver.splice(index, 1);
        }
    }

    function select(cardEl) {
        cardEl.classList.add('selectCard');
        cardElsSelected.push(cardEl);
    }

    function deselect(cardEl) {
        cardEl.classList.remove('selectCard');
        
        // remove from cardEl cardElsSelected list
        const index = cardElsSelected.indexOf(cardEl);
        if (index > -1) {
            cardElsSelected.splice(index, 1);
        }
    }

    function highlightCard(event) {
        var cardElHoveredOver = event.currentTarget; 
        addHover(cardElHoveredOver)
    }

    function unhighlightCard(event) {
        var cardElHoveredOver = event.currentTarget; 
        removeHover(cardElHoveredOver)
    }

    socket.on('board_change', function (msg) {
        var state = JSON.parse(msg);
        console.log(state)
        for (cardPosition in state.board) {
            var card = state.board[cardPosition];
            var cardEl = document.getElementById(cardPosition);
            // would be more efficient to only do the following if the card has changed which I can check by
            // looking into the HTML
            cardEl.innerHTML = '';
            if (! card.hasOwnProperty('colour') || 
                ! card.hasOwnProperty('fill')   || 
                ! card.hasOwnProperty('number') || 
                ! card.hasOwnProperty('shape')) {
                
                // keep an empty div in empty card elements so formatting is consistent
                // with non-empty card elements :)
                // var emptyShape = document.createElement("div");
                // emptyShape.classList.add("SQUIGGLE", "RED", "OPEN")
                // cardEl.appendChild(emptyShape);
                cardEl.classList.remove('cardShadow');
                removeHover(cardEl)
                deselect(cardEl)
                
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

        // disable players ability to make a move while revealing a set
        var blockDiv = document.getElementById('blockDiv');
        blockDiv.classList.add('blockDiv');

        // deselect all cards currently selected
        for (selectedCardEl of cardElsSelected) {
            selectedCardEl.classList.remove('selectCard');
        }
        cardElsSelected = []

        // remove hover from all cards currently hovered over
        for (hoveredOverCardEl of cardElsHoveredOver) {
            hoveredOverCardEl.classList.remove('hoverOverCard');
            // removeHover(hoveredOverCardEl); 
            // ^  don't use this because can't iterate over a list and change it at the same time!
        }
        cardElsHoveredOver = []


        for (cardPosition of revealedSet.cardPositions) {
            // show revealed set cards
            var cardEl = document.getElementById(cardPosition);
            cardEl.classList.add('selectCard')
        }

        setTimeout(
            function() {
                for (cardPosition of revealedSet.cardPositions) { 
                    var cardEl = document.getElementById(cardPosition);
                    cardEl.classList.remove('selectCard');
                }
                blockDiv.classList.remove('blockDiv')
            }, 
            SET_DISPLAY_TIME
        );

    });

    socket.on('error_message', function (msg) {
        console.log(msg)
    });
    
});