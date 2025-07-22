const fetchUrl: string = "https://dog.ceo/api/breeds/image/random";
const dogImage: HTMLImageElement = document.getElementById(
  "dog-image"
) as HTMLImageElement;
const loader: HTMLDivElement = document.getElementById(
  "loader"
) as HTMLDivElement;

async function showDog(): Promise<void> {
  try {
    const response: Response = await fetch(fetchUrl);
    const data: { message: string } = await response.json();
    dogImage.src = data.message;
    dogImage.hidden = false;
    loader.hidden = true;
  } catch (error) {
    console.error("Error fetching dog image:", error);
  }
}
