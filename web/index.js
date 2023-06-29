const api = 'http://localhost:8080/v1/compare';

window.onload = function () {
  const uploadForm = document.querySelector('#upload-form');
  uploadForm.onsubmit = function (event) {
    event.preventDefault();
    const file1 = document.querySelector('#oldFile').files[0];
    const file2 = document.querySelector('#newFile').files[0];

    if (file1 && file2) {
      const reader1 = new FileReader();
      reader1.readAsDataURL(file1);

      const reader2 = new FileReader();
      reader2.readAsDataURL(file2);

      // Create a new FormData object
      const formData = new FormData();

      // Append the files to the FormData object
      formData.append('oldFile', file1);
      formData.append('newFile', file2);

      // Make a POST request to the API endpoint
      fetch(api, {
        method: 'POST',
        body: formData
      })
        .then(response => response.json())
        .then(data => {
          // Handle the response data
          const oldFileIframe = document.querySelector('#oldFileHltd');
          oldFileIframe.src = oldFileIframe.src + data.oldFileBytes + '#view=fitH';
          oldFileIframe.hidden = false;

          const newFileIframe = document.querySelector('#newFileHltd');
          newFileIframe.src = newFileIframe.src + data.newFileBytes + '#view=fitH';
          newFileIframe.hidden = false;

          const  container = document.querySelector('#container');

          document.body.replaceChildren(container);

        })
        .catch(error => {
          // Handle any errors that occur during the request
          console.error(error);
        });
    }
  };
};

