const columns = document.querySelectorAll(".column");
const id_card = document.getElementsByClassName('list-group-item')[0]
const column_container = document.getElementById('player_storage_column')

export const sortOrder = []
columns.forEach((column) => {
    console.log(column.id)})

columns.forEach((column) => {
    new Sortable(column, {
        forceFallback: true,
        group: "shared",
        draggable: ".player_card",
        animation: 150,
        ghostClass: "blue-background-class",
        mirror: {
            constrainDimensions: true,
        },
        filter: 'h1',
   
        onEnd: (evt) => {
            // let newIndex;

            let neighborIndex;
            sortOrder.splice(sortOrder.indexOf(evt.item.id), 1);
            console.log("bruh")
            console.log(evt.item.id)
            if (evt.item.previousElementSibling != null && evt.item.nextElementSibling != null){
                if (evt.oldIndex < evt.newIndex) 
                    neighborIndex = sortOrder.indexOf(evt.item.previousElementSibling.id) + 1;
                else neighborIndex = sortOrder.indexOf(evt.item.nextElementSibling.id);
                    sortOrder.splice(neighborIndex, 0, evt.item.id); 
            
            }
            console.log(sortOrder)


          }
    });
});


var draftboard_bool = true
if (document.getElementById('Page Title') == "Position Rankings"){
    draftboard_bool = false
    var position_ranking_columns = {"QB":document.getElementById('QB_Column'), "RB": document.getElementById('RB_Column'),"WR":document.getElementById('WR_Column'), "TE": document.getElementById('TE_Column')}
    
}

var position_color_dict = {"QB":"purple", "RB": "lightblue","WR":"red", "TE": "blue"}
var position_ranking_dict=  {"QB":[], "RB": [],"WR":[], "TE": []}

function add_player (position, name, last_years_fantasy_points, projected_fantasy_points, team, photo_link) {
    const newPlayer = document.createElement('div')
    newPlayer.style.backgroundColor = position_color_dict[position]
    newPlayer.setAttribute("class", 'player_card')
    newPlayer.setAttribute("id", name)
    newPlayer.style.backgroundImage = 'url(' + photo_link +')'
    newPlayer.style.backgroundSize = "40% auto"
    newPlayer.style.backgroundRepeat = "no-repeat"
    newPlayer.style.backgroundPosition = "-10% 5%"
    var oNewP = document.createElement("pre");
    var first_text_node = document.createTextNode(team +" "+position +" "+ name)
    var second_text_node = document.createTextNode("2021: " + last_years_fantasy_points )
    var third_text_node = document.createTextNode("2022 Projected: " + projected_fantasy_points)
   
    newPlayer.appendChild(first_text_node)
    newPlayer.appendChild(document.createElement("br"));
    newPlayer.appendChild(second_text_node)
    newPlayer.appendChild(document.createElement("br"));
    newPlayer.appendChild(third_text_node)
    newPlayer.setAttribute("class", 'player_card')
    newPlayer.setAttribute("id", name)
    newPlayer.innerText=  team +" "+position + " "+ name  +
    "\n 2021: " + last_years_fantasy_points + "\n "+
    "2022 Projected: " + projected_fantasy_points
    // add the text node to the newly created div
    newPlayer.style.backgroundColor = position_color_dict[position]
    if (draftboard_bool == true) {
        column_container.appendChild(newPlayer)
        sortOrder.push(name)
        position_ranking_dict[position].push(name)
    }
    else{
        position_ranking_columns[position].appendChild(newPlayer)
    }  
  }


function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function() {
        if (rawFile.readyState === 4 && rawFile.status == "200") {
            callback(rawFile.responseText);
        }
    }
    rawFile.send(null);
}

//usage:


readTextFile("stat_files/rankings_list.json", function(text){
    var rankings_list = JSON.parse(text);
    var rankings_text = sessionStorage.getItem("full rankings based on position")
    var positions_rankings_list = JSON.parse(rankings_text)
    console.log(positions_rankings_list)
    if (positions_rankings_list != null){
        rankings_list = positions_rankings_list
    }

    readTextFile("stat_files/simplified_fantasy_data.json", function(text1){
        var profile_data = JSON.parse(text1)
        readTextFile("stat_files/photo_list.json", function(text2){
        var photo_dict = JSON.parse(text2)
        for (let i = 1; i < 150; i++) {
            var player_name = rankings_list[i]
            console.log(player_name)
            var profile_dict = profile_data[player_name]
            add_player(profile_dict['Pos'], player_name, profile_dict['2021 Points'], profile_dict['2022 Projected'], profile_dict['Team'],photo_dict[player_name])
            }})
    });
    
});
console.log(sortOrder.at(0))

document.getElementById('player rankings button').onclick=function(){
sessionStorage.setItem("full_rankings", JSON.stringify(sortOrder))
window.location.href = 'position_rankings.html'
}


document.getElementById('reset_button').onclick=function(){
    console.log(sessionStorage.getItem("full rankings based on position"))
    sessionStorage.removeItem("full rankings based on position")
    var hello = sessionStorage.getItem("full rankings based on position")

    console.log(hello)
    window.location.reload();
    }












