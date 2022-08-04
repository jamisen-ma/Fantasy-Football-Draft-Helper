from bing_image_urls import bing_image_urls
import json
f = open('rankings_list.json')
data = json.load(f)
photo_link_dict = {}
for key in data:
    name = data[key]
    url = bing_image_urls(name + " ESPN NFL headhsot", limit=1)[0]
    print(name)
    photo_link_dict[name] = url
    
with open('photo_list.json', 'w') as f:
    json.dump(photo_link_dict, f)
 

