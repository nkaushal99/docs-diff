// global variables to store files uploaded by the user
let oldFile, newFile;

// handle file upload events using 'browse' dialog
const oldFileBrowser = document.querySelector('#old-file-browser');
const oldFileUpload = document.querySelector('#old-file-upload');
oldFileBrowser.addEventListener('click', event => {
  event.preventDefault();
  oldFileUpload.click();
})

const newFileBrowser = document.querySelector('#new-file-browser');
const newFileUpload = document.querySelector('#new-file-upload');
newFileBrowser.addEventListener('click', event => {
  event.preventDefault();
  newFileUpload.click();
});

// Create a function that returns the callback function with the variable to change as the argument
function updateFileVar(fileVar) {
  return function (event) {
    // let the default event handling happen i.e. dont override preventDefault()
    // update the file variable
    if (fileVar === 'oldFile') {
      oldFile = event.target.files[0];
    } else {
      newFile = event.target.files[0];
    }
  };
}

// update oldFile / newFile values when files uploaded
oldFileUpload.addEventListener('change', updateFileVar('oldFile'));
newFileUpload.addEventListener('change', updateFileVar('newFile'));

// const borderContainers = document.querySelectorAll('.border-container');
function handleDragDropInAnimation(event) {
  if (event.currentTarget.classList.contains('border-container')) {
    // Set the cursor to indicate a copy operation
    event.dataTransfer.dropEffect = "copy";
    event.currentTarget.style.borderStyle = 'solid';
  }
}

function handleDragDropOutAnimation(event) {
  if (event.currentTarget.classList.contains('border-container'))
    event.currentTarget.style.borderStyle = 'dashed';
}

// Handle the dragover and dragenter event
function handleDragOver(event) {
  event.preventDefault();
  handleDragDropInAnimation(event);
}

function handleDragEnter(event) {
  handleDragOver(event);
}

// Handle the dragleave event
function handleDragLeave(event) {
  event.preventDefault();
  // Set the cursor to indicate a copy operation
  handleDragDropOutAnimation(event);
}

// handle the drop event
function updateFileVarWithDrop(fileVar) {
  return function dropHandler(event) {
    console.log('file dropped')
    // Prevent default behavior (Prevent file from being opened)
    event.preventDefault();

    handleDragLeave(event);
    const files = event.dataTransfer.files;
    if (files) {
      const file = files[0];
      // update the file variable
      if (fileVar === 'old') {
        oldFile = file;
      } else {
        newFile = file;
      }
    }
  };
}

// disable window drag n drop listener
window.addEventListener('drop', event => {
  event.preventDefault();
});
window.addEventListener('dragover', event => {
  event.preventDefault();
})

const dropZones = document.querySelectorAll('.drop-zone');
dropZones.forEach(dropZone => {
  dropZone.addEventListener('dragenter', handleDragEnter);
  dropZone.addEventListener('dragover', handleDragOver);
  if (dropZone.classList.contains('old')) {
    dropZone.addEventListener('drop', updateFileVarWithDrop('old'));
  } else {
    dropZone.addEventListener('drop', updateFileVarWithDrop('new'));
  }
  dropZone.addEventListener('dragleave', handleDragLeave);
});

const uploadForm = document.querySelector('#upload-form');
uploadForm.onsubmit = function (event) {
  event.preventDefault();
  const file1 = oldFile;
  const file2 = newFile;

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
    const api = 'http://localhost:8080/v1/compare';
    fetch(api, {
      method: 'POST',
      body: formData
    })
      .then(response => response.json())
      .then(data => {
        // Store the response in session storage
        sessionStorage.setItem('compareResponse', JSON.stringify(data));

        // Redirect to diff.html
        window.location.href = 'diff.html';
      })
      .catch(error => {
        // Handle any errors that occur during the request
        console.error(error);
      });
  }
};

