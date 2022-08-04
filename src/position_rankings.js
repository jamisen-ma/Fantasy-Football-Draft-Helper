const id_card = document.getElementsByClassName('list-group-item')[0]
const QB_container = document.getElementById('QB')//add each position column
const RB_container = document.getElementById('RB')//add each position column
const WR_container = document.getElementById('WR')//add each position column
const TE_container = document.getElementById('TE')//add each position column
const columns = document.querySelectorAll(".column");
var position_color_dict = {"QB":"purple", "RB": "green","WR":"red", "TE": "blue"}
var position_ranking_dict=  {"QB":[], "RB": [],"WR":[], "TE": []}
var position_numerical_ranking_dict=  {"QB":[], "RB": [],"WR":[], "TE": []}
var position_column_dict=  {"QB":QB_container, "RB": RB_container,"WR":WR_container, "TE": TE_container}
var name_rank_pair_dict = {}
var final_sorted_array
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
            var sortOrder = position_ranking_dict[column.id]
            let neighborIndex;
            sortOrder.splice(sortOrder.indexOf(evt.item.id), 1);
   
            if (evt.item.previousElementSibling != null && evt.item.nextElementSibling != null){
                if (evt.oldIndex < evt.newIndex) 
                    neighborIndex = sortOrder.indexOf(evt.item.previousElementSibling.id) + 1;
                else neighborIndex = sortOrder.indexOf(evt.item.nextElementSibling.id);
                    sortOrder.splice(neighborIndex, 0, evt.item.id); 
            
            }
    
            var name_array = position_ranking_dict['QB'].concat(position_ranking_dict['RB'],position_ranking_dict['WR'],position_ranking_dict['TE'])
            var overall_rank_array = [].concat(position_numerical_ranking_dict['QB'],position_numerical_ranking_dict['RB'],position_numerical_ranking_dict['WR'],position_numerical_ranking_dict['TE'])
            for (var i=0; i<name_array.length; i++) { // now lets iterate in sort order
                name_rank_pair_dict[overall_rank_array[i]] = (name_array[i])
            }
            var dict1 = {'a':[5,6],'b':[9,7]}
            position_ranking_dict['TE'].push("jijkjkjk")
            var combined = [].concat(dict1['a'],dict1['b'])
            // console.log(position_ranking_dict['TE'])
          
            var keys = Object.keys(name_rank_pair_dict); 
            keys.sort()
            var sorted_name_array = []
            for (var i=0; i<keys.length; i++) { // now lets iterate in sort order
                var value = name_rank_pair_dict[i];
                sorted_name_array.push(value)
            }

            final_sorted_array = sorted_name_array

          }
    });
});


function add_player (position, name, last_years_fantasy_points, projected_fantasy_points, team, photo_link, overall_rank) {
    const newPlayer = document.createElement('div')
    newPlayer.style.backgroundColor = position_color_dict[position]
    newPlayer.setAttribute("class", 'player_card')
    newPlayer.setAttribute("id", name)
    console.log(photo_link)
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

    // add the text node to the newly created div
    

    
    position_column_dict[position].appendChild(newPlayer)
    position_ranking_dict[position].push(name)
    position_numerical_ranking_dict[position].push(overall_rank)

    

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
var rankings_text = sessionStorage.getItem("full_rankings")
var rankings_list = JSON.parse(rankings_text)



readTextFile("stat_files/simplified_fantasy_data.json", function(text1){
    var profile_data = JSON.parse(text1)
    readTextFile("stat_files/photo_list.json", function(text2){
    var photo_dict = JSON.parse(text2)
    for (let i = 0; i < 149; i++) {
        var player_name = rankings_list[i]

        var profile_dict = profile_data[player_name]
        var name = profile_dict['Name']
        add_player(profile_dict['Pos'], name, profile_dict['2021 Points'], profile_dict['2022 Projected'], profile_dict['Team'],photo_dict[name],i)
        }})
});






document.getElementById('draftboard button').onclick=function(){
    final_sorted_array.unshift("PLACEHOLDER")
    sessionStorage.setItem("full rankings based on position", JSON.stringify(final_sorted_array))
    window.location.href = 'index.html'
    }
 








