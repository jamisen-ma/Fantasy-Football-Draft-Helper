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

columns.forEach((column) => {
    new Sortable(column, {
        group: "shared",
        animation: 150,
        ghostClass: "blue-background-class",
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
    


          }
    });
});
function sort_object(obj) {
    items = Object.keys(obj).map(function(key) {
        return [key, obj[key]];
    });
    items.sort(function(first, second) {
        return second[1] - first[1];
    });
    sorted_obj={}
    $.each(items, function(k, v) {
        use_key = v[0]
        use_value = v[1]
        sorted_obj[use_key] = use_value
    })
    return(sorted_obj)
} 

function add_player (position, name, last_years_fantasy_points, projected_fantasy_points, team, overall_rank) {
    const newPlayer = document.createElement('div')
    newPlayer.setAttribute("class", 'player_card')
    newPlayer.setAttribute("id", name)
    newPlayer.innerText=  team +" "+position + " "+ name  +
    "\n 2021: " + last_years_fantasy_points + "\n "+
    "2022 Projected: " + projected_fantasy_points
    // add the text node to the newly created div
    newPlayer.style.backgroundColor = position_color_dict[position]
    position_column_dict[position].appendChild(newPlayer)
    position_ranking_dict[position].push(name)
    position_numerical_ranking_dict[position].push(overall_rank)
    var name_array = [].concat(position_ranking_dict['QB'],position_ranking_dict['RB'],position_ranking_dict['WR'],position_ranking_dict['TE'])
    var overall_rank_array = [].concat(position_numerical_ranking_dict['QB'],position_numerical_ranking_dict['RB'],position_numerical_ranking_dict['WR'],position_numerical_ranking_dict['TE'])
    var unordered_ranked_dict = name_array.map((x, i) => ({ x, y: overall_rank_array[i]}));
    var items = Object.keys(unordered_ranked_dict).map(function(key) {
        return [key, unordered_ranked_dict[key]];
      });
      
      // Sort the array based on the second element
      items.sort(function(first, second) {
        return second[1] - first[1];
      });
    console.log(unordered_ranked_dict)

    

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
    for (let i = 0; i < 149; i++) {
        var player_name = rankings_list[i]

        var profile_dict = profile_data[player_name]
   
        add_player(profile_dict['Pos'], profile_dict['Name'], profile_dict['2021 Points'], profile_dict['2022 Projected'], profile_dict['Team'],i)
        }
});


document.getElementById('draftboard button').onclick=function(){

    var name_array = [].concat(position_ranking_dict['QB'],position_ranking_dict['RB'],position_ranking_dict['WR'],position_ranking_dict['TE'])
    var overall_rank_array = [].concat(position_numerical_ranking_dict['QB'],position_numerical_ranking_dict['RB'],position_numerical_ranking_dict['WR'],position_numerical_ranking_dict['TE'])
    var unordered_ranked_dict = overall_rank_array[i].map((x, i) => ({ x, y: name_array[i]}));
    var items = Object.keys(unordered_ranked_dict).map(
        (key) => { return [unordered_ranked_dict[key], key] });
      
      // Step - 2
      // Sort the array based on the second element (i.e. the value)
      items.sort(
        (first, second) => { return first[1] - second[1] }
      );
      
      // Step - 3
      // Obtain the list of keys in sorted order of the values.
      var ordered_rank_list = items.map(
        (e) => { return e[0] });
    console.log(ordered_rank_list)
      
    ordered_rank_list.unshift("PLACEHOLDER")
    sessionStorage.setItem("full rankings based on position", JSON.stringify(ordered_rank_list))
    window.location.href = 'draftboard.html'
    }









