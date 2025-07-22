const fetchUrl = "https://dog.ceo/api/breeds/image/random";
const imgElement = document.getElementById("dog-image");
const loaderElement = document.getElementsByClassName("loader");

async function showDog() {
    try {
        const response = await fetch(fetchUrl);
        const data = await response.json();
        imgElement.src = data.message;
        imgElement.hidden = false;
        loaderElement[0].hidden = true;
    } catch(error) {
        console.error("Error fetching dog image:", error);
    }
}