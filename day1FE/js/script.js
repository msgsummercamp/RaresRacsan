const fetchUrl = "https://dog.ceo/api/breeds/image/random";
const imgElement = document.getElementById("dog-image");    

async function showDog() {
    try {
        const response = await fetch(fetchUrl); // Fetch a random dog image
        const data = await response.json();     // Parse the JSON response
        imgElement.src = data.message;          // Set the image source to the dog image URL
    } catch(error) {
        console.error("Error fetching dog image:", error);
    }
}