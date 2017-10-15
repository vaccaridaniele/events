import requests
import json

'POST'
headers = {'Content-Type': 'application/json'}
#url = 'http://test-events.jelastic.dogado.eu/events/rest/eventscrapes'
url = 'http://localhost:8080/events/rest/eventscrapes'
payload = {"title": "titolo da pythonn","description": "description pythonnnnn"}
r = requests.post(url, data=json.dumps(payload), headers=headers)
print(r)
